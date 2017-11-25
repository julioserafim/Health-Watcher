package healthwatcher.model.complaint;

import healthwatcher.model.address.Address;
import healthwatcher.model.employee.Employee;
import lib.util.Date;

//classe queixa eh uma classe basica que tem subclasses
public abstract class Complaint implements java.io.Serializable {

	public static final int QUEIXA_ALIMENTAR = 1;

	public static final int QUEIXA_ANIMAL = 2;

	public static final int QUEIXA_DIVERSA = 3;

	private int codigo;

	private String solicitante;

	private String descricao;

	private String observacao;

	private String email;

	private Employee atendente;

	private int situacao;

	private Date dataParecer;

	private Date dataQueixa;

	private Address enderecoSolicitante;

	private long timestamp; // para tratamento de concorrencia (scbs)

	public Complaint() {
	}

	public Complaint(String solicitante, String descricao, String observacao, String email,
			Employee atendente, int situacao, Date dataParecer, Date dataQueixa,
			Address enderecoSolicitante, long timestamp) {

		//Numero fica vazio por enquanto - no Repositorio ele eh inicializado
		this.codigo = 0;
		this.solicitante = solicitante;
		this.descricao = descricao;
		this.observacao = observacao;
		this.email = email;
		this.atendente = atendente;
		this.situacao = situacao;
		this.dataParecer = dataParecer;
		this.dataQueixa = dataQueixa;
		this.enderecoSolicitante = enderecoSolicitante;
		this.timestamp = timestamp;
	}

	public Employee getAtendente() {
		return atendente;
	}

	public void setAtendente(Employee atendente) {
		this.atendente = atendente;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getDataParecer() {
		return dataParecer;
	}

	public void setDataParecer(Date dataParecer) {
		this.dataParecer = dataParecer;
	}

	public Date getDataQueixa() {
		return dataQueixa;
	}

	public void setDataQueixa(Date dataQueixa) {
		this.dataQueixa = dataQueixa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getEnderecoSolicitante() {
		return enderecoSolicitante;
	}

	public void setEnderecoSolicitante(Address enderecoSolicitante) {
		this.enderecoSolicitante = enderecoSolicitante;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void incTimestamp() {
		this.timestamp = timestamp + 1;
	}
}