package org.plugin;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Developer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "print-developers", requiresProject = true, defaultPhase = LifecyclePhase.VALIDATE)
public class PrintDevelopersMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        List<Developer> developers = project.getOriginalModel().getDevelopers();

        if (CollectionUtils.isEmpty(developers)) {
            getLog().warn("В проекте нет информации о разработчиках.");
            return;
        }

        getLog().info("Информация о разработчиках:");
        developers.forEach(this::printDeveloperInfo);
    }

    private void printDeveloperInfo(Developer dev) {
        getLog().info("------------------------------");
        logField("ID", dev.getId());
        logField("Имя", dev.getName());
        logField("Email", dev.getEmail());
        logField("Организация", dev.getOrganization());
        logRoles(dev.getRoles());
        logField("Часовой пояс", dev.getTimezone());
        logField("URL", dev.getUrl());
    }

    private void logField(String fieldName, String value) {
        String output = StringUtils.defaultIfBlank(value, "не указано");
        getLog().info(fieldName + ": " + output);
    }

    private void logRoles(List<String> roles) {
        String rolesOutput = CollectionUtils.isEmpty(roles)
                ? "не указаны"
                : roles.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(", "));

        getLog().info("Роли: " + rolesOutput);
    }
}