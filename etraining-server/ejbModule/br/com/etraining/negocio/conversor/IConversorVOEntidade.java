package br.com.etraining.negocio.conversor;

import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.modelo.def.entidades.IBean;

public interface IConversorVOEntidade<ENTIDADE extends IBean, VO extends IVO> {

	public ENTIDADE fromVO(VO vo);

	public VO toVO(ENTIDADE entidade);

}
