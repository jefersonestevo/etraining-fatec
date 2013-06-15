package br.com.etraining.client.vo.impl.exercicios;

import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.interfaces.IVO;

public class AlteraExercicioVO implements IVO {

    private static final long serialVersionUID = 4754802173023890926L;

    private ExercicioVO exercicio;

    public ExercicioVO getExercicio() {
        return exercicio;
    }

    public void setExercicio(ExercicioVO exercicio) {
        this.exercicio = exercicio;
    }

}
