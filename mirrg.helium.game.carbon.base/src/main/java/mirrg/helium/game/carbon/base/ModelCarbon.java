package mirrg.helium.game.carbon.base;

import java.util.function.Consumer;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class ModelCarbon<G extends GameCarbon<?, ?>>
{

	@XStreamOmitField
	private ControllerCarbon<G> controller;

	public ControllerCarbon<G> getController()
	{
		return controller;
	}

	protected ControllerCarbon<G> createController(G game)
	{
		return null;
	}

	//

	public void getChildModels(Consumer<ModelCarbon<? super G>> dest)
	{

	}

	public void getAllModels(Consumer<ModelCarbon<? super G>> dest)
	{
		dest.accept(this);
		getChildModels(dest);
	}

	/**
	 * XStreamによって生成された直後に呼び出されなければならない。
	 */
	public void initialize(G game)
	{
		getChildModels(d -> d.initialize(game));
		controller = createController(game);
	}

	public void dispose()
	{
		if (controller != null) controller.dispose();
		getChildModels(d -> d.dispose());
	}

}
