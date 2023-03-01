// This is your test publishable API key.

let stripe = Stripe(stripePublicKey);
// The items the customer wants to buy

let stripeElements;
let elements;

loadPaymentForm();
checkStatus();

$('#payment-form').submit(handleSubmit);

async function loadPaymentForm() {
  try {
    const { clientSecret } = await $.ajax('/create-payment-intent', {
      method: 'POST',
      contentType: 'application/json;charset=utf-8',
      data: JSON.stringify(paymentIntent),
      traditional: true,
    });

    const appearance = { theme: 'stripe' };
    elements = stripe.elements({ appearance, clientSecret });
    const paymentElement = elements.create('payment', { layout: 'tabs' });
    paymentElement.mount('#payment-element');
  } catch (error) {
    displayErrorAlert('There was error creasting the payment intent.');
  }
}

async function handleSubmit(e) {
  e.preventDefault();
  setLoading(true);

  const response = await stripe.confirmPayment({
    elements,
    confirmParams: {},
    redirect: 'if_required',
  });

  if (response.error) {
    displayErrorAlert(response.error.message);
  } else {
    document.location.href = `/invoices/${paymentIntent.invoiceId}/pay/stripe/${response.paymentIntent.client_secret}`;
  }

  setLoading(false);
}

// Fetches the payment intent status after payment submission
async function checkStatus() {
  const clientSecret = new URLSearchParams(window.location.search).get(
    'payment_intent_client_secret'
  );

  if (!clientSecret) {
    return;
  }

  const { paymentIntent } = await stripe.retrievePaymentIntent(clientSecret);

  switch (paymentIntent.status) {
    case 'succeeded':
      showMessage('Payment succeeded!');
      break;
    case 'processing':
      showMessage('Your payment is processing.');
      break;
    case 'requires_payment_method':
      showMessage('Your payment was not successful, please try again.');
      break;
    default:
      showMessage('Something went wrong.');
      break;
  }
}

function displayErrorAlert(message) {
  $('#errorStripeMessage').html(message);
  $('#errorStripePayment').toast('show');
}

// Show a spinner on payment submission
function setLoading(isLoading) {
  if (isLoading) {
    // Disable the button and show a spinner
    $('#btnSubmit').addClass('disabled');
    $('#btnCancel').addClass('disabled');
    $('#spinner').show();
    $('#btnSubmitText').hide();
  } else {
    $('#btnSubmit').removeClass('disabled');
    $('#btnCancel').removeClass('disabled');
    $('#spinner').hide();
    $('#btnSubmitText').show();
  }
}
