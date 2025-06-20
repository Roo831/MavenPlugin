package org.plugin;

import java.util.List;

import org.apache.maven.model.Developer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "print-developers", requiresProject = true)
public class PrintDevelopersMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        List<Developer> developers = project.getOriginalModel().getDevelopers();

        if (developers == null || developers.isEmpty()) {
            getLog().info("В проекте нет информации о разработчиках.");
            return;
        }

        getLog().info("Информация о разработчиках:");
        for (Developer dev : developers) {
            getLog().info("------------------------------");
            getLog().info("ID: " + dev.getId());
            getLog().info("Имя: " + dev.getName());
            getLog().info("Email: " + dev.getEmail());
            getLog().info("Организация: " + dev.getOrganization());
            getLog().info("Роли: " + String.join(", ", dev.getRoles()));
            getLog().info("Часовой пояс: " + dev.getTimezone());
        }
    }
}
