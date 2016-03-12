package competition.subsystems.autonomous;

import com.google.inject.Provider;

import competition.subsystems.portcullis_wheels.PortcullisWheelsSubsystem;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import xbot.common.properties.XPropertyManager;

public class TraversePortcullisCommandGroup extends CommandGroup{
    
    public TraversePortcullisCommandGroup(
            PortcullisWheelsSubsystem portcullisWheels,
            XPropertyManager propManager,
            Provider<RaiseArmAndTraverseDefenseCommandGroup> raiseArmAndTraverseProvider){
        RaiseArmAndTraverseDefenseCommandGroup raiseArmAndTraverse = raiseArmAndTraverseProvider.get();
        
        this.addParallel(raiseArmAndTraverse);
        
        SpinPortcullisWheelsCommand spinPortcullisWheels = new SpinPortcullisWheelsCommand(portcullisWheels, propManager);
        spinPortcullisWheels.setDirection(SpinPortcullisWheelsCommand.PortcullisDirection.Up);
        
        this.addParallel(spinPortcullisWheels);
    }
}
