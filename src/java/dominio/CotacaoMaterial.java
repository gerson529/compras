package dominio;
// Generated 19/03/2014 01:45:43 by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * CotacaoMaterial generated by hbm2java
 */
public class CotacaoMaterial extends EntidadeDominio implements java.io.Serializable {


     
     private Cotacao cotacao;
     private Material material;
     private Float valorUnitario;
     private Date prazoEntrega;
     private String observacao;

    public CotacaoMaterial() {
    }

    public CotacaoMaterial(Cotacao cotacao, Material material, Float valorUnitario, Date prazoEntrega, String observacao) {
       this.cotacao = cotacao;
       this.material = material;
       this.valorUnitario = valorUnitario;
       this.prazoEntrega = prazoEntrega;
       this.observacao = observacao;
    }
   
    
    public Cotacao getCotacao() {
        return this.cotacao;
    }
    
    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }
    public Material getMaterial() {
        return this.material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    public Float getValorUnitario() {
        return this.valorUnitario;
    }
    
    public void setValorUnitario(Float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    public Date getPrazoEntrega() {
        return this.prazoEntrega;
    }
    
    public void setPrazoEntrega(Date prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }
    public String getObservacao() {
        return this.observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }




}


