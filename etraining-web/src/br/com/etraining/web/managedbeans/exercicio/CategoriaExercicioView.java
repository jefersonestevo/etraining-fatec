package br.com.etraining.web.managedbeans.exercicio;

import java.util.ArrayList;
import java.util.List;

import br.com.etraining.client.vo.impl.entidades.CategoriaExercicioVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;

public class CategoriaExercicioView extends CategoriaExercicioVO {

    private static final long serialVersionUID = -6136349397198918614L;
    
    private List<ExercicioVO> listaExercicios = new ArrayList<ExercicioVO>();

    public CategoriaExercicioView(Long id, String titulo) {
        super();
        setId(id);
        setTitulo(titulo);
    }

    public List<ExercicioVO> getListaExercicios() {
        return listaExercicios;
    }

    public void setListaExercicios(List<ExercicioVO> listaExercicios) {
        this.listaExercicios = listaExercicios;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


}
