package healthwatcher.model.complaint;

import healthwatcher.model.address.Address;
import healthwatcher.model.employee.Employee;
import lib.util.Date;

public class SpecialComplaint extends Complaint {

	private short idade;

	private String instrucao;

	private String ocupacao;

	private Address enderecoOcorrencia;

	//construtor vazio
	public SpecialComplaint() {
	}

	public SpecialComplaint(String solicitante, String descricao, String observacao, String email,
			Employee atendente, int situacao, Date dataParecer, Date dataQueixa,
			Address enderecoSolicitante, short idade, String instrucao, String ocupacao,
			Address enderecoOcorrencia) {

		//inicializar tambem o tipo da queixa
		super(solicitante, descricao, observacao, email, atendente, situacao, dataParecer,
				dataQueixa, enderecoSolicitante, 0);

		this.idade = idade;
		this.instrucao = instrucao;
		this.ocupacao = ocupacao;
		this.enderecoOcorrencia = enderecoOcorrencia;
	}

	public Address getEnderecoOcorrencia() {
		return enderecoOcorrencia;
	}

	public void setEnderecoOcorrencia(Address enderecoOcorrencia) {
		this.enderecoOcorrencia = enderecoOcorrencia;
	}

	public short getIdade() {
		return idade;
	}

	public void setIdade(short idade) {
		this.idade = idade;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

	public String getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}
}