package mirrg.helium.game.carbon.base;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.thoughtworks.xstream.XStream;

import mirrg.helium.standard.hydrogen.event.EventManager;

public class GameCarbon<THIS extends GameCarbon<THIS, MODEL>, MODEL extends ModelCarbon<THIS>>
{

	public GameCarbon(MODEL model)
	{
		setModel(model);
	}

	@SuppressWarnings("unchecked")
	private THIS getThis()
	{
		return (THIS) this;
	}

	//

	private EventManager<EventGameCarbon> eventManager = new EventManager<>();

	public EventManager<EventGameCarbon> event()
	{
		return eventManager;
	}

	//

	private MODEL model;

	public MODEL getModel()
	{
		return model;
	}

	public void setModel(MODEL model)
	{
		event().post(new EventGameCarbon.ChangeModel.Pre());

		model.initialize(getThis());
		MODEL tmp = this.model;
		this.model = model;
		if (tmp != null) tmp.dispose();

		event().post(new EventGameCarbon.ChangeModel.Post());
	}

	//

	public XStream getXStream()
	{
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		return xStream;
	}

	//

	private ArrayList<ControllerCarbon<? super THIS>> tools = new ArrayList<>();

	public void addTool(ControllerCarbon<? super THIS> tool)
	{
		tools.add(tool);
	}

	public Stream<ControllerCarbon<? super THIS>> getTools()
	{
		return tools.stream();
	}

}
