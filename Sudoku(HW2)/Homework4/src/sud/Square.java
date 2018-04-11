package sud;

import java.awt.*;

public class Square {
	int x;
	int y;
	int n;
	Color oldColor = Color.CYAN;
	
	public Square (int n, int x, int y){
		this.n = n;
		this.x = x;
		this.y = y;
	}

	public Square (int n, int x, int y, Color color){
		this.n = n;
		this.x = x;
		this.y = y;
		this.oldColor = color;
	}

	public int getN (){
		return n;
	}
	
	public void setN(int n){
		this.n = n;
	}
	
}
