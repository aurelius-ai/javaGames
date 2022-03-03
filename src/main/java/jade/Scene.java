package jade;

public abstract class Scene {
	

	public Scene() {
		System.out.println("Inside scene");
	}
	
	public abstract void update(float dt);
	
	
	
}
