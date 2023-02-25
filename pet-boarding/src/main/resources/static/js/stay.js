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

let newCount;
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

function addOrUpdateAdditionalService(add, id, index) {
  const tableBody = $('#additionalServicesTable > tbody');
  if (add) {
    index = currentIndex;
    let newId = 'new_' + newCount++;
    let template = $($('#newAdditionalServiceRow')[0].content.cloneNode(true));
    template.find('tr').attr('data-index', index);
    let data = {};
    for (let { name, value } of $('#addServiceForm').serializeArray()) {
      data[name] = value;
      template
        .find(`input[field=_${name}]`)
        .val(value)
        .attr('id', (i, value) => value.replace('__index__', index))
        .attr('name', (i, value) => value.replace('__index__', index));
      template.find(`td[field=_${name}]`).text(value);
    }
    template
      .find('td[field=_serviceText]')
      .text($('#addService option:selected').text());
    template
      .find('#_btnEdit')
      .click(() => openAdditionalServicesDialog(newId, index));
    template
      .find('#_btnRemove')
      .click(() => removeServicesDialog(newId, index));
    $('#additionalServicesTable > tbody').append(template);
    currentIndex++;
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
  newCount = 0;
  additionalServiceDialog = new bootstrap.Modal('#addServiceDialog', {});
});
