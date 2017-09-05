Page.setAppImgDir("recursos/imagens/");

HLayout.create
({
    ID: "Ajuda", width: "100%", height: "100%", members:
    [
        TreeGrid.create
        ({
            ID: "Indice", width: 300, minWidth: 200, showResizeBar: true, showHeader: false, border: "none",
            folderIcon: "livro.png", nodeIcon: "pagina.png", fields: [{name: "titulo"}], data: topicos,
            bodyBackgroundColor: "#FCFCFC", backgroundColor: "#FCFCFC",
            recordClick: function(viewer, record, recordNum, field, fieldNum, value, rawValue)
            {
                Conteudo.setContentsURL(record.html);
            }
        }),
        VLayout.create
        ({
            members:
            [
                HTMLPane.create
                ({
                    ID: "Conteudo", contentsType: "page"
                })
            ]
        })
    ]
});

Indice.data.openAll();
Conteudo.setContentsURL("ajuda.html");