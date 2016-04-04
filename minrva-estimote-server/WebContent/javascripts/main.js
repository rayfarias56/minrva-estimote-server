// The root URL for the RESTful services
// TODO (rfarias2) I believe everything is in a global namespace right now (see: https://google.github.io/styleguide/javascriptguide.xml#Naming)
// TODO could be more object-oriented: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Introduction_to_Object-Oriented_JavaScript
// TODO resolve url issue "minrva-estimote-server"
// Needs to be updated before production pushes
var rootURL = "/minrva-estimote-server/rest/v1.0"; // Needs to be updated before production.
//var rootURL = "/rest/v1.0"; // prod
var token = null;

$(document).ready(function() {
	showLogInBox();
	// Insert the 'add beacon' boxes at the top of the page
	$('#beacons').append(getBeaconDiv('', '', '', '', '', '', '', true));
	getBeacons();
});

function authenticateUser() {
	$('#login-button-box').prepend($('<img>', {src: '/minrva-estimote-server/images/loading.gif', class: 'loading-img', align: 'right'}));
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL + '/user/authenticate/',
		dataType: "json",
		data: JSON.stringify({
			"username": $('#username').val(), 
			"password": $('#password').val(), 
		}),
		success: function(newTokenJsonResponse) {
			token = newTokenJsonResponse.token;
			$('#login-container').remove();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Invalid username / password');
		}
	});
}

$("#version").click(function() {
	$.ajax({
		url: rootURL + '/version/',
		type: 'GET',
		dataType: 'json',
		success: function(version) {
			alert("Database Version: " + version.id);
		},
		error: function(e) {
			console.log("Failed to get Version");
		}
	});
});

function toJSON($beacon) {
	return JSON.stringify({
		"uuid": $beacon.find('.uuid').val(), 
		"major": $beacon.find('.major').val(), 
		"minor": $beacon.find('.minor').val(), 
		"x": Number($beacon.find('.x').val()).toFixed(2), 
		"y": Number($beacon.find('.y').val()).toFixed(2), 
		"z": Number($beacon.find('.z').val()).toFixed(2), 
		"description": $beacon.find('.desc').val()
	});
}

function toPath($beacon) {
	var uuid = $beacon.find('.uuid').val();
	var major = $beacon.find('.major').val();
	var minor = $beacon.find('.minor').val();
	return rootURL + '/beacons/' + uuid + '/' + major + '/' + minor;
}

function getBeacons(searchKey) {
	$.ajax({
		type: 'GET',
		url: rootURL + '/beacons/',
		dataType: "json",
		success: populateBeaconList,
		error: function() {
			alert('Server failed to respond');
		}
	});
}

function updateBeacon($beacon) {
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: toPath($beacon),
		headers: {
			"Authorization": "Bearer " + token
		},
		dataType: "json",
		data: toJSON($beacon),
		error: function(jqXHR, textStatus, errorThrown) {
			alert('Update error: ' + textStatus);
			// replaceLogInBox();
		}
	});
}

function populateBeaconList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	
	var uuids = [];
	$.each(list, function(index, beacon) {
		if (uuids.indexOf(beacon.uuid) == -1) {
			$('#uuid-list').append('<option value="' + beacon.uuid + '">');
			uuids.push(beacon.uuid);
		}
	});

	// $('#beacon-list:not(:first)').remove();
	$.each(list, function(index, beacon) {
		var uuid = beacon.uuid;
		var major = beacon.major;
		var minor = beacon.minor;
		var x = beacon.x.toFixed(2);
		var y = beacon.y.toFixed(2);
		var z = beacon.z.toFixed(2);
		var desc = beacon.description;
		
		var $beacon = getBeaconDiv(uuid, major, minor, x, y, z, desc, false);
		$('#beacons').append($beacon);
		autosize.update($beacon.find('.desc'));
	});
}

function createBeacon($beacon) {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL + '/beacons/',
		headers: {
			"Authorization": "Bearer " + token
		},
		dataType: "json",
		data: toJSON($beacon),
		error: function(jqXHR, textStatus, errorThrown){
			alert('Create error: ' + textStatus);
			// replaceLogInBox();
		}
	});
}

function deleteBeacon($beacon) {
	$.ajax({
		type: 'DELETE',
		url: toPath($beacon),
		headers: {
			"Authorization": "Bearer " + token
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Delete error: ' + textStatus);
			// replaceLogInBox();
		}
	});
}

function editBeacon($beacon) {
	console.log('edit');
	$beacon.find('.coord').prop('disabled', false);
	$beacon.find('.desc').prop('disabled', false);
	var $button = $beacon.find('.edit-save')
	$button.unbind();
	$button.click(function() {
		console.log('save');
		$beacon.find('.coord').prop('disabled', true);
		$beacon.find('.desc').prop('disabled', true);
		updateBeacon($beacon);
		$button.val('Edit');
		$button.unbind();
		$button.click(function() {
			editBeacon($beacon);
		});
	});
	$button.val('Save');
}

function getBeaconDiv(uuid, major, minor, x, y, z, desc, create) {
	var $uuid = $('<input>', {class: 'beacon-item beacon-input uuid', name: 'uuid', type: 'text', list: 'uuid-list', maxlength: '36', value: uuid});
	var $major = $('<input>', {class: 'beacon-item beacon-input mm major', name: 'major', type: 'number', min: '0', max: '65535', value: major});
	var $minor = $('<input>', {class: 'beacon-item beacon-input mm minor', name: 'minor', type: 'number', min: '0', max: '65535', value: minor});
	var $x = $('<input>', {class: 'beacon-item beacon-input coord x', name: 'x', type: 'number', step: '0.01', value: x});
	var $y = $('<input>', {class: 'beacon-item beacon-input coord y', name: 'y', type: 'number', step: '0.01', value: y});
	var $z = $('<input>', {class: 'beacon-item beacon-input coord z', name: 'z', type: 'number', step: '0.01', value: z});
	var $desc = $('<textarea>', {class: 'beacon-item beacon-input desc', name: 'desc', value: desc, rows: 1});
	autosize($desc)
	
	var $beacon = $('<tr>', {class: 'beacon'});
	$.each([$uuid, $major, $minor, $x, $y, $z, $desc], function(index, $item) {
		var $wrapper = $('<td>', {class: 'wrapper'}).append($item);
		$beacon.append($wrapper);
	});
	
	if (create) {
		var $create = $('<input>', {class: 'beacon-item', id: 'create', type: 'button', value: 'Create', width: '110px'});
		$create = $('<td>', {class: 'wrapper', colspan: '2'}).append($create);
		$beacon.append($create);
		$create.click(function() {
			createBeacon($beacon);
			$('#beacons').append(getBeaconDiv(
					$beacon.find('.uuid').val(),
					$beacon.find('.major').val(),
					$beacon.find('.minor').val(),
					Number($beacon.find('.x').val()).toFixed(2),
					Number($beacon.find('.y').val()).toFixed(2),
					Number($beacon.find('.z').val()).toFixed(2),
					$beacon.find('.desc').val()));
			$beacon.find('.beacon-input').val('');
			autosize.update($beacon.find('.desc'));
		});
	}
	else {
		var $edit = $('<input>', {class: 'beacon-item edit-save', type: 'button', value: 'Edit'});
		var $delete = $('<input>', {class: 'beacon-item delete', type: 'button', value: 'Delete'});
		$beacon.append($('<td>', {class: 'wrapper'}).append($edit), $('<td>', {class: 'wrapper'}).append($delete));
		$edit.click(function() {
			editBeacon($beacon);
		});
		$delete.click(function() {
			deleteBeacon($beacon);
			$beacon.remove();
		});
		$beacon.find('.beacon-input').prop('disabled', true);
	}
	
	return $beacon;
}

function showLogInBox() {
	var box = $('<div>', {id: 'login-box'});
	box.append($('<label>', {for: 'username'}).text('Username'));
	box.append($('<br />'));
	box.append($('<input>', {id: 'username', type: 'text'}));
	box.append($('<br />'));
	box.append($('<label>', {for: 'password'}).text('Password'));
	box.append($('<br />'));
	box.append($('<input>', {id: 'password', type: 'password'}));
	box.append($('<br />'));
	box.append($('<div>', {id: 'login-button-box'}).append($('<button>', {onclick: 'authenticateUser()'}).text('Log In')));
	$('#content').prepend($('<div>', {id: 'login-container'}).append(box));
}