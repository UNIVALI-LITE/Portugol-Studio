var is = false
var op = 1
$(document).ready(function(){
  var height = $( window ).height()
  testOS()
  $('section').css('min-height',height)
  $('#nav-download').hide()
  swapC()
})

function testOS(){
  // system
  var unknown = ' - '
  var nAgt = navigator.userAgent
  var os = unknown;
  var clientStrings = [
    {s:'Windows 10', r:/(Windows 10.0|Windows NT 10.0)/},
    {s:'Windows 8.1', r:/(Windows 8.1|Windows NT 6.3)/},
    {s:'Windows 8', r:/(Windows 8|Windows NT 6.2)/},
    {s:'Windows 7', r:/(Windows 7|Windows NT 6.1)/},
    {s:'Windows Vista', r:/Windows NT 6.0/},
    {s:'Windows Server 2003', r:/Windows NT 5.2/},
    {s:'Windows XP', r:/(Windows NT 5.1|Windows XP)/},
    {s:'Windows 2000', r:/(Windows NT 5.0|Windows 2000)/},
    {s:'Windows ME', r:/(Win 9x 4.90|Windows ME)/},
    {s:'Windows 98', r:/(Windows 98|Win98)/},
    {s:'Windows 95', r:/(Windows 95|Win95|Windows_95)/},
    {s:'Windows NT 4.0', r:/(Windows NT 4.0|WinNT4.0|WinNT|Windows NT)/},
    {s:'Windows CE', r:/Windows CE/},
    {s:'Windows 3.11', r:/Win16/},
    {s:'Android', r:/Android/},
    {s:'Open BSD', r:/OpenBSD/},
    {s:'Sun OS', r:/SunOS/},
    {s:'Linux', r:/(Linux|X11)/},
    {s:'iOS', r:/(iPhone|iPad|iPod)/},
    {s:'Mac OS X', r:/Mac OS X/},
    {s:'Mac OS', r:/(MacPPC|MacIntel|Mac_PowerPC|Macintosh)/},
    {s:'QNX', r:/QNX/},
    {s:'UNIX', r:/UNIX/},
    {s:'BeOS', r:/BeOS/},
    {s:'OS/2', r:/OS\/2/},
    {s:'Search Bot', r:/(nuhk|Googlebot|Yammybot|Openbot|Slurp|MSNBot|Ask Jeeves\/Teoma|ia_archiver)/}
  ];
  for (var id in clientStrings) {
    var cs = clientStrings[id];
    if (cs.r.test(nAgt)) {
      os = cs.s;
      break;
    }
  }

  var osVersion = unknown;

  if (/Windows/.test(os)) {
    osVersion = /Windows (.*)/.exec(os)[1];
    os = 'Windows';
  }

  switch (os) {
    case 'Mac OS X':
      osVersion = /Mac OS X (10[\.\_\d]+)/.exec(nAgt)[1];
      break;

    case 'Android':
      osVersion = /Android ([\.\_\d]+)/.exec(nAgt)[1];
      break;

    case 'iOS':
      osVersion = /OS (\d+)_(\d+)_?(\d+)?/.exec(nVer);
      osVersion = osVersion[1] + '.' + osVersion[2] + '.' + (osVersion[3] | 0);
      break;
  }
  var path = 'img/'
  var link = 'https://github.com/UNIVALI-LITE/Portugol-Studio/releases/download/v2.6/portugol-studio.2.6.linux.x64.zip'
  if(os === 'Windows'){
    path += 'win'
    link = 'https://github.com/UNIVALI-LITE/Portugol-Studio/releases/download/v2.6/portugol-studio.2.6.windows.exe'
  }else if(os === 'Mac OS X'){
    path += 'mac'
    link = 'https://github.com/UNIVALI-LITE/Portugol-Studio/releases/download/v2.6/portugol-studio.2.6.osx.zip'
  }else{
    path += 'lin'
    $('.outras').append('(x32)')
  }
  $('.download-link').attr('href',link)
  path += '.svg'
  $('#os-logo').attr('src',path)
}

$( window ).scroll(function() {
  var height = $('#recursos').offset().top/2
  var pos = $(window).scrollTop()

  if(pos > height){
    if(!$('#nav-download').is(':visible')){
      $('#nav-download').show(100)
    }
  }
  else{
    if($('#nav-download').is(':visible')){
      $('#nav-download').hide(100)
    }
  }
})

function swapC() {
  $('.arrow').animate({ opacity: op }, 1000)
  if(is){
    op = 1
  }else{
    op = 0.5
  }
  is = !is
  window.setTimeout(function() { swapC() }, 1000)
}

$(".goto").click(function(){
  var height = $(this.dataset.to).offset().top
  $("html, body").animate({ scrollTop:  height}, "ease")
  if(this.dataset.to = '#recursos'){
    $('#nav-download').show(100)
  }
})
