package dominio;
// Generated 19/03/2014 01:45:43 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Cotacao generated by hbm2java
 */
public class Cotacao  extends EntidadeDominio  implements java.io.Serializable {


    
     private Compra compra;
     private Integer fornecedorId;
    private Boolean selecionado;

    
    private Set cotacaoMaterials = new HashSet(0);

    public Cotacao() {
    }
    
    public Cotacao(Integer id) {
        this.setId(id);
        
    }
    
    public Cotacao(Compra compra, Integer fornecedorId, Set cotacaoMaterials) {
       this.compra = compra;
       this.fornecedorId = fornecedorId;
       this.cotacaoMaterials = cotacaoMaterials;
    }
   
    
    public Compra getCompra() {
        return this.compra;
    }
    
    public void setCompra(Compra compra) {
        this.compra = compra;
    }
    public Integer getFornecedorId() {
        return this.fornecedorId;
    }
    
    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }
    public Set getCotacaoMaterials() {
        return this.cotacaoMaterials;
    }
    
    public void setCotacaoMaterials(Set cotacaoMaterials) {
        this.cotacaoMaterials = cotacaoMaterials;
    }




}


