package mro.base.System.config.basicType;

public enum SecondItemType {
	SECOND_TYPE1, //不同規格，可替代
	SECOND_TYPE2; //同規格，不同MAKER

	@Override
	public String toString() {
		return name() != null ? name() : null;
	}

	/** SECOND SOURCE */
	public boolean secondSource(){
		if(name().equals(SECOND_TYPE2.name())) return true;
		return false;
	}
}
