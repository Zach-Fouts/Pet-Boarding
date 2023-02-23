let endDate = {
	input: null,
	value: null,
	disabled: true,
	button: null,
	icons: ['bi-x-circle', 'bi-pen'],
	message: ['Cancel changes to end date', 'Change end date'],
};

function toggleEndDate() {
	let disabled = (endDate.disabled = !endDate.disabled);
	endDate.input.prop('disabled', disabled);
	endDate.button.attr('title', endDate.message[+disabled]);
	endDate.button
		.children('i')
		.attr('class', 'bi')
		.addClass(endDate.icons[+disabled]);
}

let additionalServices = [];

// additional services
function addAdditionalService() {
	$('#btnAddService').removeAttr('data-bs-toggle');
}

$(function () {
	endDate.input = $('#endDate');
	endDate.value = endDate.input.val();
	endDate.button = $('#btnEnableEndDate');
});
