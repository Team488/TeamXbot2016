package competition.subsystems.autonomous;

import com.google.inject.Inject;
import com.google.inject.Provider;

import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class TraversePortcullisCommandGroup extends CommandGroup{
    
    @Inject
    public TraversePortcullisCommandGroup(
            Provider<RaiseArmAndTraverseDefenseCommandGroup> raiseArmAndTraverseProvider,
            Provider<SpinPortcullisWheelsCommand> spinPortcullisWheelsProvider){
        RaiseArmAndTraverseDefenseCommandGroup raiseArmAndTraverse = raiseArmAndTraverseProvider.get();
        
        this.addParallel(raiseArmAndTraverse);
        
        SpinPortcullisWheelsCommand spinPortcullisWheels = spinPortcullisWheelsProvider.get();
        spinPortcullisWheels.setDirection(SpinPortcullisWheelsCommand.PortcullisDirection.Up);
        
        this.addParallel(spinPortcullisWheels);
    }
}
