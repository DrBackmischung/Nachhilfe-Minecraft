package de.wi2020sebgroup1.minecraft;

public class Main extends JavaPlugin {

    private ConfigurableApplicationContext context;

    @Override
    @SneakyThrows
    public void onEnable() {
        getLogger().info("will be enabled...");
        saveDefaultConfig();
        ResourceLoader loader = new DefaultResourceLoader(getClassLoader());
        SpringApplication application = new SpringApplication(loader, Application.class);
        application.addInitializers(new SpringSpigotInitializer(this));
        context = application.run();
        getLogger().info("enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("will be disabled...");
        context.close();
        context = null;
        getLogger().info("disabled");
    }

}
