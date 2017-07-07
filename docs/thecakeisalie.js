$(document).ready(function()
{
    var controller = this;

    $('#validateButton').click(function()
    {
      controller.validate();
    });

    $('#startButton').click(function(e)
    {
      var userName = $('#userName').val().trim().toUpperCase();

      if (userName.length == 0)
      {
        $('#userName').focus();
        return alert('Você deve informar seu nome completo antes de iniciar o desafio');
      }

      controller.generateTable();

      $('#startButton').attr('disabled', 'true');
      $('#userName').attr('disabled', 'true');

      setTimeout(function()
      {
        $('#collapseExample').removeClass('collapse');

        scrollTo('#collapseExample');

      }, 500);
    });

    controller.random = function(max, min)
    {
      max = max || 1;
      min = min || 0;

      controller.randomSeed = (controller.randomSeed * 9301 + 49297) % 233280;

      var rnd = controller.randomSeed / 233280;
      var n;

      n = max + 1 - min;
      n = Math.trunc(rnd * n);

      return min + n;
    };

    controller.generateTable = function()
    {
      var userName = $('#userName').val().trim().toUpperCase()

      var table = '<table class="table table-responsive"><tbody>'
      var matrix = controller.generateMatrix(userName)
      var csv = ''

      for (var i = 0; i < 10; i++)
      {
        table = table + '<tr>'

        for (var j = 0; j < 10; j++)
        {
            table = table + '<td>' + matrix[i][j] + '</td>'
            csv = csv + matrix[i][j]
            if(j<9){
              csv = csv+','
            }
        }
        if(i<9){
          csv = csv+'\n'
        }
        table = table + '</tr>';
      }

      table = table + '</tbody></table>'

      createDownloadLink('matrix'+userName+'.csv', csv)
      $('#matrix').append(table);

    }


    controller.generateMatrix = function(userName, userPeriod)
    {
      var seed = 0;

      for (var i = 0; i < userName.length; i++)
      {
        seed = seed + controller.letterToNumber(userName[i]);
      }

      controller.randomSeed = seed;

      var matrix = new Array(10);

      for (var i = 0; i < matrix.length; i++)
      {
        var line = new Array(10);

        for (var j = 0; j < matrix.length; j++)
        {
          line[j] = 0;
        }

        matrix[i] = line;
      }

      var numberOfMultiples = 30;
      var multiplier = controller.letterToNumber(userName[0]);

      for (var i = 1; i <= numberOfMultiples; i++)
      {
        var l = controller.random(0, 9);
        var c = controller.random(0, 9);

        var pos = (l * 10) + c;

        while (l == 0 || matrix[l][c] != 0)
        {
          pos = (pos + 1) % 100;

          l = Math.floor(pos / 10);
          c = pos % 10;
        }

        matrix[l][c] = controller.random(1, 1000) * multiplier;
      }

      for (var i = 0; i < 10; i++)
      {
        for (var j = 0; j < 10; j++)
        {
          if (matrix[i][j] == 0)
          {
            var n = controller.random(2, 27000);

            if (controller.isMultiple(n, multiplier))
            {
              n = n + 1;
            }

            matrix[i][j] = n;
          }
        }
      }

      controller.matrix = matrix;

      return matrix;
    };

    controller.generateCode = function(userName)
    {
      var multiplier = controller.letterToNumber(userName[0]);
      var multiplier2 = controller.letterToNumber(userName[userName.length - 1]);

      var matrix = controller.matrix;

      var total = 0;

      for (var i = 0; i < matrix.length; i++)
      {
        for (var j = 0; j < matrix.length; j++)
        {
          var n = matrix[i][j];

          if (controller.isMultiple(n, multiplier))
          {
            n = n + j;
            n = n * i;

            total = total + n;
          }
        }
      }

      total = total * multiplier2;

      total = total + "";

      var code = "";

      for (var i = 0; i < total.length; i++)
      {
        var digit = total.charAt(i);

        if (digit != '0' && digit != '1')
        {
          code = code + controller.numberToLetter(parseInt("" + digit));
        }
        else
        {
            code = code + digit;
        }
      }

      return code;
    }

    controller.validate = function()
    {
      var userName = $('#userName').val().trim().toUpperCase();
      var userAnswer = $('#userAnswer').val().trim().toUpperCase();

      if (userAnswer.length == 0)
      {
        return alert('Você deve informar o código a ser validado');
      }

      var code = controller.generateCode(userName);

      if (code == userAnswer)
      {
        alert('Parabéns, seu código está correto!', 'correct.gif');
      }
      else
      {
        alert('Que pena, seu código está incorreto!', 'incorrect.gif');
      }
    };

    controller.isMultiple = function(number, multiple)
    {
      return number % multiple == 0;
    };

    controller.letterToNumber = function(letter)
    {
      letter = letter + "";

      return letter.charCodeAt(0) - 63;
    };

    controller.numberToLetter = function(number)
    {
      return String.fromCharCode(number + 63).charAt(0);
    }
});
