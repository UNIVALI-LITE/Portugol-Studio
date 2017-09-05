/*
 * JsMin
 * Portugolscript Compressor
 * http://www.crockford.com/
 * http://www.smallsharptools.com/
 */

dp.sh.Brushes.Portugol = function()
{
    var keywords =
    
        'programa inteiro real caracter cadeia logico const ' +
        'para pare retorne escolha funcao caso e ou nao ' + 
        'verdadeiro falso se senao enquanto faca contrario ' + 
        'inclua biblioteca vazio';
        
    this.regexList = 
    [
        {regex: dp.sh.RegexLib.SingleLineCComments, css: 'comment'}, 
        {regex: dp.sh.RegexLib.MultiLineCComments, css: 'comment'}, 
        {regex: dp.sh.RegexLib.DoubleQuotedString, css: 'string'}, 
        {regex: dp.sh.RegexLib.SingleQuotedString, css: 'string'}, 
        {regex: new RegExp('\\b([\\d]+(\\.[\\d]+)?|0x[a-f0-9]+)\\b', 'gi'), css: 'number'}, 
        {regex: new RegExp(this.GetKeywords(keywords), 'gm'), css: 'keyword'}
    ];

    this.CssClass = 'dp-j';
    this.Style = '.dp-j .annotation { color: #646464; }' + '.dp-j .number { color: #C00000; }';
};

dp.sh.Brushes.Portugol.prototype = new dp.sh.Highlighter();
dp.sh.Brushes.Portugol.Aliases = ['portugol'];
