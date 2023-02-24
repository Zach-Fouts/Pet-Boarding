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

let additionalServices;
let additionalServiceDialog;

// additional services
function addAdditionalService() {
  if (!$('#additionalServicesContainer').is(':visible'))
    $('#additionalServicesContainer').show(200);
  openAdditionalServicesDialog();
}

function openAdditionalServicesDialog(id, index) {
  $('.add-input').val('').trigger('change');
  if (id) {
    $('#btnSaveAdditionalService')
      .click(() => addOrUpdateAdditionalService(false, id, index))
      .text('Update');
    let data = additionalServices[id];
    for (let name in data) {
      $('[name="' + name + '"]')
        .val(data[name])
        .trigger('change');
    }
  } else {
    // new service
    $('#btnSaveAdditionalService')
      .click(() => addOrUpdateAdditionalService(true, id, index))
      .html('Add <i class="bi-plus"></i>');
  }
  additionalServiceDialog.show();
}

function addOrUpdateAdditionalService(add) {
  const asTableBody = $('#additionalServicesTable > tbody');
  if (add) {
  }
}

function updateUnitPrice(select) {
  $('#addPricePerUnit')
    .val(select.selectedOptions[0].dataset.priceperunit)
    .trigger('change');
}

function updateSubtotal() {
  const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
  });
  let subTotal = $('#addQuantity').val() * $('#addPricePerUnit').val();
  $('#addSubTotal').val(formatter.format(subTotal));
}

$(function () {
  endDate.input = $('#endDate');
  endDate.value = endDate.input.val();
  endDate.button = $('#btnEnableEndDate');
  //
  additionalServiceDialog = new bootstrap.Modal('#addServiceDialog', {});
});
