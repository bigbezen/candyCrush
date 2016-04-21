package CandyCrashGame;


public interface Visitor {
	public void visit(RegularCandy regular);
	public void visit(StripCandyVertical stripV);
	public void visit(StripCandyHorizontal stripH);
	public void visit(WrappedCandy candy);
	public void visit(ColorBomb bomb);
}
