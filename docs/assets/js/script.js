var os = ''
$('.download-link').click(function () {
  ga('send', {
    hitType: 'event',
    eventCategory: 'download',
    eventAction: versionInfo.versionString,
    eventLabel: os,
    eventValue: 1
  })
})
$('.outra-plataforma').click(function () {
  ga('send', {
    hitType: 'event',
    eventCategory: 'download',
    eventAction: versionInfo.versionString,
    eventLabel: 'Para Outra Plataforma',
    eventValue: 1
  })
})

function testOS () {
  var nAgt = navigator.userAgent
  var clientStrings = [
    {s: 'Windows 10', r: /(Windows 10.0|Windows NT 10.0)/},
    {s: 'Windows 8.1', r: /(Windows 8.1|Windows NT 6.3)/},
    {s: 'Windows 8', r: /(Windows 8|Windows NT 6.2)/},
    {s: 'Windows 7', r: /(Windows 7|Windows NT 6.1)/},
    {s: 'Windows Vista', r: /Windows NT 6.0/},
    {s: 'Windows Server 2003', r: /Windows NT 5.2/},
    {s: 'Windows XP', r: /(Windows NT 5.1|Windows XP)/},
    {s: 'Windows 2000', r: /(Windows NT 5.0|Windows 2000)/},
    {s: 'Windows ME', r: /(Win 9x 4.90|Windows ME)/},
    {s: 'Windows 98', r: /(Windows 98|Win98)/},
    {s: 'Windows 95', r: /(Windows 95|Win95|Windows_95)/},
    {s: 'Windows NT 4.0', r: /(Windows NT 4.0|WinNT4.0|WinNT|Windows NT)/},
    {s: 'Windows CE', r: /Windows CE/},
    {s: 'Windows 3.11', r: /Win16/},
    {s: 'Android', r: /Android/},
    {s: 'Open BSD', r: /OpenBSD/},
    {s: 'Sun OS', r: /SunOS/},
    {s: 'Linux', r: /(Linux|X11)/},
    {s: 'iOS', r: /(iPhone|iPad|iPod)/},
    {s: 'Mac OS X', r: /Mac OS X/},
    {s: 'Mac OS', r: /(MacPPC|MacIntel|Mac_PowerPC|Macintosh)/},
    {s: 'QNX', r: /QNX/},
    {s: 'UNIX', r: /UNIX/},
    {s: 'BeOS', r: /BeOS/},
    {s: 'OS/2', r: /OS\/2/},
    {s: 'Search Bot', r: /(nuhk|Googlebot|Yammybot|Openbot|Slurp|MSNBot|Ask Jeeves\/Teoma|ia_archiver)/}
  ]
  for (var id in clientStrings) {
    var cs = clientStrings[id]
    if (cs.r.test(nAgt)) {
      os = cs.s
      break
    }
  }
  $('.download-link').attr('href', versionInfo.links.others)
  $('.outra-plataforma').attr('href', versionInfo.links.others)

  var path = 'assets/img/'
  var link = versionInfo.links.linux_64
  if (os.indexOf('Windows') !== -1) {
    path += 'win'
    link = versionInfo.links.windows
  } else if (os === 'Mac OS X') {
    path += 'mac'
    link = versionInfo.links.osx
  } else {
    path += 'lin'
    link = versionInfo.links.linux_x64
  }
  $('.download-link').attr('href', link)
}

function calculateDownloads () {
  $.get('https://api.github.com/repos/UNIVALI-LITE/Portugol-Studio/releases', function (data) {
    var result = 100000
    for (var i = 0; i < data.length; i++) {
      for (var j = 0; j < data[i].assets.length; j++) {
        result += data[i].assets[j].download_count
      }
    }
    $('.download-count').html(result)
  })
}

$(document).ready(function () {
  testOS()
  calculateDownloads()
})
