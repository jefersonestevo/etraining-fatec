package br.com.etraining.negocio.bo.impl.exercicios;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import br.com.etraining.client.vo.impl.exercicios.AlteraExercicioVO;
import br.com.etraining.client.vo.impl.exercicios.RespostaConsultaExercicioVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.exception.ETrainingInfraException;
import br.com.etraining.modelo.dao.interfaces.IDaoExercicio;
import br.com.etraining.modelo.entidades.EntExercicio;
import br.com.etraining.negocio.bo.interfaces.AbstractBO;
import br.com.etraining.negocio.conversor.impl.ConversorExercicio;

@Named("AlteraExercicioVO")
public class AlteracaoExercicioBO extends AbstractBO<AlteraExercicioVO, RespostaConsultaExercicioVO> {

    private static Logger log = Logger.getLogger(AlteracaoExercicioBO.class);

    @Inject
    private IDaoExercicio exercicioDao;

    @Inject
    private ConversorExercicio conversorExercicio;

    @Override
    protected RespostaConsultaExercicioVO executarRegrasEspecificas(AlteraExercicioVO request)
        throws ETrainingException {

        EntExercicio exercicioAtual = exercicioDao.pesquisar(request.getExercicio().getId());
        Long id = exercicioAtual.getId();

        try {
            EntExercicio exercicio = conversorExercicio.fromVO(request.getExercicio());
            BeanUtils.copyProperties(exercicioAtual, exercicio);
        }
        catch (IllegalAccessException e) {
            log.error("Erro ao copiar dados do exercicio para alteracao", e);
            throw new ETrainingInfraException(CodigoExcecao.ERRO_DESCONHECIDO);
        }
        catch (InvocationTargetException e) {
            log.error("Erro ao copiar dados do exercicio para alteracao", e);
            throw new ETrainingInfraException(CodigoExcecao.ERRO_DESCONHECIDO);
        }
        exercicioAtual.setId(id);
        EntExercicio exerc = exercicioDao.alterar(exercicioAtual);

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
