package it.uniroma1.commons.queue.object;

import it.uniroma1.commons.entity.Fine;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Fined {

    private String code;

    private List<Fine> fines;

    private String name;

    private String surname;

}