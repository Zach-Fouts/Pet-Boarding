function updateContactInformation(select) {
	$('#contact').html(select.find('option:selected').attr('data-contact'));
}

$(function () {});
