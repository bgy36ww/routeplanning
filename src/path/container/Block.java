package path.container;

class Block{
	//helper class used to construct the planning map
	public int dir;//direction
	public String value;//ID of the robot
	public Block(int d, String s){
		this.dir=d;
		this.value=s;
	}
}
