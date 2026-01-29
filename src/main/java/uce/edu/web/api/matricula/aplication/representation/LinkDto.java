package uce.edu.web.api.matricula.aplication.representation;

// contiene 2 elementos, el link descriptivo:
public class LinkDto {
    public String href;
    public String rel;

    public LinkDto(){
        
    }

    public LinkDto(String href, String rel){
        this.href = href;
        this.rel = rel;
    }


}
