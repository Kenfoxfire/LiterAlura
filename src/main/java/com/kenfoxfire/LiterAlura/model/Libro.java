package com.kenfoxfire.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private List<String> subjects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)  // Define la clave foránea
    private Autor autor;  //
    private String language;
    private boolean copyright;
    private String mediaType;
    private Integer downloadCount;

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.title = datosLibro.title();
        this.subjects = datosLibro.subjects();
        this.language = datosLibro.languages().isEmpty() ? null : datosLibro.languages().getFirst();
        this.copyright = datosLibro.copyright();
        this.mediaType = datosLibro.mediaType();
        this.downloadCount = datosLibro.downloadCount();
        this.autor = autor;
    }

    public Libro(){}

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", subjects='" + subjects + '\'' +
                ", authors=" + autor +
                ", languages='" + language + '\'' +
                ", copyright=" + copyright +
                ", mediaType='" + mediaType + '\'' +
                ", downloadCount=" + downloadCount +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

}
