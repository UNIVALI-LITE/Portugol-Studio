$(document).on('change', ':file', function() {
  var input = $(this)
  const target = input.data('target');
  const $target = $(target);
  $('#fileSpan').attr('data-content',input.val().replace(/\\/g, '/').replace(/.*\//, ''))
  handleFileSelect()
});

function handleFileSelect()
  {
    if (!window.File || !window.FileReader || !window.FileList || !window.Blob) {
      alert('The File APIs are not fully supported in this browser.');
      return;
    }

    input = document.getElementById('file');
    if (!input) {
      alert("Um, couldn't find the fileinput element.");
    }
    else if (!input.files) {
      alert("This browser doesn't seem to support the `files` property of file inputs.");
    }
    else if (!input.files[0]) {
      alert("Please select a file before clicking 'Load'");
    }
    else {
      file = input.files[0];
      fr = new FileReader();
      fr.onload = receivedText;
      //fr.readAsText(file);
      fr.readAsDataURL(file);
    }
  }

  function receivedText() {
    var text = fr.result
    text = text.replace('data:application/vnd.ms-excel;base64,','')
    text = text.replace('"','')
    //text = decodeURIComponent(text)
    text = atob(text)
    makeAMatrix(text)
  }

  function makeAMatrix(text){
    var matrix = new Array(10);
    for (var i = 0; i < 10; i++) {
      matrix[i] = new Array(10);
    }
    text = text.replace(/\n/g, ',')
    var linhas = text.split(",")
    for(var i = 0 ; i < 10 ; i++){
      for(var j = 0 ; j < 10 ; j++){
        // if(j==9 && i==0){
        //   alert('asdasd: '+linhas[i*10+j].charCodeAt(5), true)
        // }
        matrix[i][j] = parseInt(linhas[i*10+j])
      }
    }
    var name = $('#userName').val().trim().toUpperCase()
    var letra = name.charCodeAt(0)-63
    var soma = 0
    for(var i = 0 ; i < 10 ; i++){
      for(var j = 0 ; j < 10 ; j++){
        if(matrix[i][j] % letra == 0){
          soma = soma + ((matrix[i][j]+j)*i)
        }
      }
    }
    var uletra = name.charCodeAt(name.length-1)-63
    var fim = ''+(soma*uletra)
    var result = '';
    for(var i = 0 ; i < fim.length ; i++){
      var l = parseInt(fim.charAt(i))
      if(l == 0 || l == 1){
        result = result + l
      }else{
        result = result + String.fromCharCode(63 + l)
      }
    }
    alert(result)
  }
