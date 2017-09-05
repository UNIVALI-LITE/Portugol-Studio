
window.tenteVoceMesmo = function(arquivo)
{
    var portugol = window.parent.portugol;
    var exemplos = window.parent.exemplos;

    if (portugol && exemplos)
    {
        var indice = -1;

        for (var i = 0; i < exemplos.length; i++)
        {
            if (exemplos[i].caminho === arquivo)
            {
                indice = i;
                break;
            }
        }

        if (indice >= 0)
        {
            $.get(arquivo, function(conteudo)
            {
                portugol.abrirExemplo(conteudo);

            }, "text");
        }
        else
        {
            alert("Erro");
        }
    }
    else
    {
        alert("Esta funcionalidade só está disponível dentro do Portugol Studio!");
    }
};

function exibirConteudo(arquivo)
{
    var conteudo = window.parent.Conteudo;

    conteudo.setContentsURL(arquivo);
}

// Função que permite executar loops de forma assíncrona e executar 
// uma função de callback ao final do loop

function asyncLoop(iterations, func, callback)
{
    var index = 0;
    var done = false;
    var loop =
    {
        next: function()
        {
            loop.index = index;

            if (done)
            {
                return;
            }

            if (index < iterations)
            {
                index++;
                func(loop);
            }
            else
            {
                done = true;
                callback();
            }
        },
        iteration: function()
        {
            return index - 1;
        },
        break: function()
        {
            done = true;
            callback();
        }
    };

    loop.next();

    return loop;
}

String.prototype.escapeHtml = function()
{
    return this.replace(/&/g, '&amp;')
    .replace(/>/g, '&gt;')
    .replace(/</g, '&lt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&apos;');
};

function obterExemplos()
{
    var exemplos = [];

    $(".codigo-portugol").each(function(indice, elemento)
    {
        exemplos.push({div: elemento, caminho: elemento.dataset.file});
    });

    return exemplos;
}

function carregarExemplos(callback)
{
    var exemplos = obterExemplos();

    asyncLoop(exemplos.length, function(loop)
    {
        $.get(exemplos[loop.index].caminho, function(content)
        {
            exemplos[loop.index].codigo = content;
            loop.next(); // Executa a próxima iteração do loop

        }, "text");
    },
    function()
    {
        callback(exemplos);
    });
}

function corrigirBugScrollHorizontal()
{
    $("ol.dp-j").each(function(indiceOl, elementoOl)
    {
        var maiorLargura = 0;

        $("li", elementoOl).each(function(indiceLi, elementoLi)
        {
            var estiloAnterior = $(elementoLi).css("position");
            var larguraLi = $(elementoLi).css("position", "absolute").width();

            if (larguraLi > maiorLargura)
            {
                maiorLargura = larguraLi;
            }                    

            $(elementoLi).css("position", estiloAnterior);
        });

        $(elementoOl).css("min-width", maiorLargura + 20);
    });
}

$(document).ready(function()
{
    carregarExemplos(function(exemplos)
    {
        asyncLoop(exemplos.length, function(loop)
        {
            var exemplo = exemplos[loop.index];
            var codigo = exemplo.codigo;
            var div = exemplo.div;
            var titulo = div.dataset.title;
            var tipo = div.dataset.type;

            var htmlCabecalho = "<div>" + titulo.escapeHtml();

            /*
             if (tipo === "exemplo")
             {
             htmlCabecalho = htmlCabecalho + " - <a href=\"javascript:tenteVoceMesmo('" + exemplo.caminho + "');\">Tente você mesmo</a>";
             }*/

            htmlCabecalho = htmlCabecalho + "</div>";

            var cabecalho = $(htmlCabecalho).attr
            ({
                class: "cabecalho-codigo-portugol"
            });

            $(div).append(cabecalho).append($("<pre>" + codigo.escapeHtml() + "</pre>").attr
            ({
                name: "code", class: "portugol:nocontrols"
            }));

            loop.next(); // Executa a próxima iteração do loop
        },
        function()
        {
            window.parent.exemplos = exemplos;
            dp.SyntaxHighlighter.HighlightAll('code');
			
			corrigirBugScrollHorizontal();
        });
    });
});
    