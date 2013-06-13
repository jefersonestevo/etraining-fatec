package br.com.etraining.negocio.bo.impl.exercicios;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.etraining.client.vo.impl.exercicios.InsereExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaExercicioVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorExercicio;

@Named("InsereExercicioVO")
public class InsercaoExercicioBO extends AbstractBO<InsereExercicioVO, RespostaConsultaExercicioVO> {

    @Inject
    private IDaoExercicio exercicioDao;

    @Inject
    private ConversorExercicio conversorExercicio;

    @Override
    protected RespostaConsultaExercicioVO executarRegrasEspecificas(InsereExercicioVO request)
        throws ETrainingException {

        EntExercicio exercicio = conversorExercicio.fromVO(request.getExercicio());
        exercicioDao.inserir(exercicio);
        
        EntExercicio exerc = exercicioDao.pesquisar(exercicio.getId());
        RespostaConsultaExercicioVO resposta = new RespostaConsultaExercicioVO();
        resposta.setExercicio(conversorExercicio.toVO(exerc));
        return resposta;
    }

    public IDaoExercicio getExercicioDao() {
        return exercicioDao;
    }

    public void setExercicioDao(IDaoExercicio exercicioDao) {
        this.exercicioDao = exercicioDao;
    }

    public ConversorExercicio getConversorExercicio() {
        return conversorExercicio;
    }

    public void setConversorExercicio(ConversorExercicio conversorExercicio) {
        this.conversorExercicio = conversorExercicio;
    }

}
