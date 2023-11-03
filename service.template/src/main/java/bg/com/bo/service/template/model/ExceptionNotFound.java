package bg.com.bo.service.template.model;

public class ExceptionNotFound extends RuntimeException{
    public ExceptionNotFound(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "ExceptionNotFound";
    }
}
