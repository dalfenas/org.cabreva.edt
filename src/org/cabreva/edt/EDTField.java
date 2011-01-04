package org.cabreva.edt;

public class EDTField {

	private String id;
	private String value;
        private TypeField type;

        public enum TypeField {
            TEXT("text"),
            SELECT("select");
            
            private String id;
            
            private TypeField(String id) {
                this.id = id;
            }

           /* public String toString() {
                return id;
            }*/

            public static TypeField fromString(String item) {
                for (TypeField field : values()) {
                    if (field.id.equalsIgnoreCase(item)){
                        return field;
                    }
                }
                return null;
            }
        }
	
	public EDTField() {
		
	}
	
	public EDTField(String id) {
		super();
		this.id=id;
	}
	
	public EDTField(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

        public EDTField(String id, TypeField type) {
                super();
                this.id = id;
                this.type = type;
        }
        
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

        public TypeField getType() {
            return type;
        }

        public void setType(TypeField type) {
            this.type = type;
        }	
	
}
