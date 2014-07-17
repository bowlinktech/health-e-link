package com.ut.healthelink.model;

import com.ut.healthelink.validator.NoHtml;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "MACRO_NAMES")
public class Macros {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @NotEmpty
    @NoHtml
    @Column(name = "MACRO_NAME", nullable = false)
    private String macroName;
   
    @NotEmpty
    @NoHtml
    @Column(name = "MACRO_SHORT_NAME", nullable = false)
    private String macroShortName;

    @Column(name = "REF_NUMBER", nullable = false)
    private Integer refNumber;

    @NoHtml
    @Column(name = "DATE_DISPLAY", nullable = true)
    private String dateDisplay;

    @NotEmpty
    @NoHtml
    @Column(name = "FORMULA", nullable = false)
    private String formula;

    @NoHtml
    @Column(name = "INVALID_WHEN", nullable = true)
    private String invalidWhen = null;

    @NoHtml
    @Column(name = "FIELDA_QUESTION", nullable = true)
    private String fieldAQuestion = null;

    @NoHtml
    @Column(name = "FIELDB_QUESTION", nullable = true)
    private String fieldBQuestion = null;

    @NoHtml
    @Column(name = "CON1_QUESTION", nullable = true)
    private String con1Question = null;

    @NoHtml
    @Column(name = "CON2_QUESTION", nullable = true)
    private String con2Question = null;
    
    @Column(name = "populateFieldA", nullable = true)
    private boolean populateFieldA = true;

    @Column(name = "CategoryId", nullable = true)
    private int categoryId;

    
	public boolean isPopulateFieldA() {
		return populateFieldA;
	}

	public void setPopulateFieldA(boolean populateFieldA) {
		this.populateFieldA = populateFieldA;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmacroName() {
        return macroName;
    }

    public void setmacroName(String macroName) {
        this.macroName = macroName;
    }

    public String getmacroShortName() {
        return macroShortName;
    }

    public void setmacroShortName(String macroShortName) {
        this.macroShortName = macroShortName;
    }

    public int getrefNumber() {
        return id;
    }

    public void setrefNumber(int refNumber) {
        this.refNumber = refNumber;
    }
    
    public String getdateDisplay() {
        return dateDisplay;
    }

    public void setdateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }
    
    public String getformula() {
        return formula;
    }

    public void setformula(String formula) {
        this.formula = formula;
    }
    
    public String getinvalidWhen() {
        return invalidWhen;
    }

    public void setinvalidWhen(String invalidWhen) {
        this.invalidWhen = invalidWhen;
    }
    
    public String getfieldAQuestion() {
        return fieldAQuestion;
    }

    public void setfieldAQuestion(String fieldAQuestion) {
        this.fieldAQuestion = fieldAQuestion;
    }
    
    public String getfieldBQuestion() {
        return fieldBQuestion;
    }

    public void setfieldBQuestion(String fieldBQuestion) {
        this.fieldBQuestion = fieldBQuestion;
    }      
    
    public String getcon1Question() {
        return con1Question;
    }

    public void setcon1Question(String con1Question) {
        this.con1Question = con1Question;
    }      
    
    public String getcon2Question() {
        return con2Question;
    }

    public void setcon2Question(String con2Question) {
        this.con2Question = con2Question;
    }

	public String getMacroName() {
		return macroName;
	}

	public void setMacroName(String macroName) {
		this.macroName = macroName;
	}

	public String getMacroShortName() {
		return macroShortName;
	}

	public void setMacroShortName(String macroShortName) {
		this.macroShortName = macroShortName;
	}

	public int getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(Integer refNumber) {
		this.refNumber = refNumber;
	}

	public String getDateDisplay() {
		return dateDisplay;
	}

	public void setDateDisplay(String dateDisplay) {
		this.dateDisplay = dateDisplay;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getInvalidWhen() {
		return invalidWhen;
	}

	public void setInvalidWhen(String invalidWhen) {
		this.invalidWhen = invalidWhen;
	}

	public String getFieldAQuestion() {
		return fieldAQuestion;
	}

	public void setFieldAQuestion(String fieldAQuestion) {
		this.fieldAQuestion = fieldAQuestion;
	}

	public String getFieldBQuestion() {
		return fieldBQuestion;
	}

	public void setFieldBQuestion(String fieldBQuestion) {
		this.fieldBQuestion = fieldBQuestion;
	}

	public String getCon1Question() {
		return con1Question;
	}

	public void setCon1Question(String con1Question) {
		this.con1Question = con1Question;
	}

	public String getCon2Question() {
		return con2Question;
	}

	public void setCon2Question(String con2Question) {
		this.con2Question = con2Question;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	} 

}
