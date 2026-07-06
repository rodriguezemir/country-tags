package site.zvolcan.countrytags.command;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import site.zvolcan.countrytags.data.Country;
import site.zvolcan.countrytags.manager.CountryManager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CountryTagCommand implements CommandExecutor {

    private static final String INPUT_KEY = "country";

    private final CountryManager countryManager;

    public CountryTagCommand(CountryManager countryManager) {
        this.countryManager = countryManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command."));
            return true;
        }

        player.showDialog(buildDialog(player));
        return true;
    }

    private Dialog buildDialog(Player player) {
        Country current = countryManager.getCountry(player.getUniqueId()).orElse(null);

        List<SingleOptionDialogInput.OptionEntry> entries = Arrays.stream(Country.values())
                .sorted(Comparator.comparing(Country::getName))
                .map(country -> SingleOptionDialogInput.OptionEntry.create(
                        country.getCountryCode(),
                        Component.text(country.getName()),
                        country == current
                ))
                .toList();

        DialogInput countryInput = DialogInput.singleOption(INPUT_KEY, Component.text("Country"), entries)
                .labelVisible(true)
                .build();

        ActionButton confirm = ActionButton.builder(Component.text("Confirm"))
                .action(DialogAction.customClick(
                        (view, audience) -> {
                            String code = view.getText(INPUT_KEY);
                            Country selected = Country.fromCode(code).orElse(null);
                            if (selected != null && audience instanceof Player target) {
                                countryManager.setCountry(target.getUniqueId(), selected);
                                target.sendMessage(Component.text("Country set to " + selected.getName() + "."));
                            }
                        },
                        ClickCallback.Options.builder().uses(1).build()
                ))
                .build();

        ActionButton cancel = ActionButton.builder(Component.text("Cancel")).build();

        return Dialog.create(factory -> factory.empty()
                .base(DialogBase.builder(Component.text("Select your country"))
                        .body(List.of(DialogBody.plainMessage(Component.text("Choose the country you want to display."))))
                        .inputs(List.of(countryInput))
                        .build())
                .type(DialogType.confirmation(confirm, cancel)));
    }

}
