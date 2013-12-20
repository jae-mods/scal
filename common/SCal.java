package scal.common;

import scal.client.TickHandler;
import scal.guns.GunType;
import scal.guns.ItemGun;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "SCal", name = "SCal", version = "0.1.0")
@NetworkMod(channels = {"SCal"}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class SCal 
{
	@Instance(value = "SCal")
	public static SCal Instance;
	
	@SidedProxy(clientSide = "scal.client.ClientProxy", serverSide = "scal.common.CommonProxy")
	public static CommonProxy Proxy;
	
	private Configuration _config;
	
	public static CreativeTabs gunTab = new CreativeTabs("gunTab");
	
	public static ItemGun m9 = new ItemGun(GunType.PistolM9);
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		this._config = new Configuration(event.getSuggestedConfigurationFile());
		this._config.load();
		VariableHandler.ItemID = this._config.get("General", "ItemIDs", 15000).getInt(15000);
		VariableHandler.BreaksGlass = this._config.get("General", "BreaksGlass", false).getBoolean(false);
		this._config.save();
		
		event.getModMetadata().autogenerated = false;
		event.getModMetadata().url = "http://scalmod.wikia.com/wiki/SCalMod_Wiki";
		event.getModMetadata().credits = "YoSoCal";
		event.getModMetadata().description = "Have fun!";
		
		this.Proxy.registerSounds();
		TickRegistry.registerScheduledTickHandler(new TickHandler(), Side.CLIENT);
	}
	
	@EventHandler
	public void Load(FMLInitializationEvent event)
	{
		LanguageRegistry.instance().addStringLocalization("itemGroup.gunTab", "en_US", "SCal Mod");
	}
}
