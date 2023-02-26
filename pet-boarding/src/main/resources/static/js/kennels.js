function modalClick(id) {

  if( id == 1){
    var modal1 = document.getElementById("myModal1");
    modal1.style.display = "block";
  }
  else if(id == 2){
    var modal2 = document.getElementById("myModal2");
    modal2.style.display = "block";
  }
  else if(id == 3){
    var modal3 = document.getElementById("myModal3");
    modal3.style.display = "block";
  }
  else if(id == 4){
    var modal4 = document.getElementById("myModal4");
    modal4.style.display = "block";
  }
  else if(id == 5){
    var modal5 = document.getElementById("myModal5");
    modal5.style.display = "block";
  }
  else if(id == 6){
    var modal6 = document.getElementById("myModal6");
    modal6.style.display = "block";
  }
  else if(id == 7){
    var modal7 = document.getElementById("myModal7");
    modal7.style.display = "block";
  }
  else if(id == 8){
    var modal8 = document.getElementById("myModal8");
    modal8.style.display = "block";
  }
  else if(id == 9){
    var modal9 = document.getElementById("myModal9");
    modal9.style.display = "block";
  }
}

function closeModal(id){
    if(id == "close1"){
    var modal1 = document.getElementById("myModal1");
        modal1.style.display = "none";
    }
    else if(id == "close2"){
        var modal2 = document.getElementById("myModal2");
        modal2.style.display = "none";
    }
    else if(id == "close3"){
        var modal3 = document.getElementById("myModal3");
        modal3.style.display = "none";
    }
    else if(id == "close4"){
        var modal4 = document.getElementById("myModal4");
        modal4.style.display = "none";
    }
    else if(id == "close5"){
       var modal5 = document.getElementById("myModal5");
        modal5.style.display = "none";
    }
    else if(id == "close6"){
        var modal6 = document.getElementById("myModal6");
        modal6.style.display = "none";
    }
    else if(id == "close7"){
        var modal7 = document.getElementById("myModal7");
        modal7.style.display = "none";
    }
    else if(id == "close8"){
        var modal8 = document.getElementById("myModal8");
        modal8.style.display = "none";
    }
    else if(id == "close9"){
        var modal9 = document.getElementById("myModal9");
        modal9.style.display = "none";
    }
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal1) {
    modal1.style.display = "none";
  }
  else if (event.target == modal2) {
    modal2.style.display = "none";
  }
  else if (event.target == modal3) {
    modal3.style.display = "none";
  }
  else if (event.target == modal4) {
    modal4.style.display = "none";
  }
  else if (event.target == modal5) {
    modal5.style.display = "none";
  }
  else if (event.target == modal6) {
    modal6.style.display = "none";
  }
  else if (event.target == modal7) {
    modal7.style.display = "none";
  }
  else if (event.target == modal8) {
    modal8.style.display = "none";
  }
  else if (event.target == modal9) {
    modal9.style.display = "none";
  }
}
