package br.com.etraining.jobs.programatreinamento;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import br.com.etraining.jobs.EtrainingJob;

@Singleton
public class JobAtualizacaoProgramaTreinamento implements EtrainingJob {

	@PostConstruct
	@Schedule(dayOfWeek = "*", hour = "*/12", minute = "*", second = "*", persistent = false)
	public void executar() {
	}

}
