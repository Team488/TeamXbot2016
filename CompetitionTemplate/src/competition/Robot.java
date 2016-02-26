
package competition;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import competition.subsystems.arm.arm_commands.UpdateArmSensorsCommand;
import competition.subsystems.autonomous.selection.AutonomousModeSelector;
import competition.subsystems.vision.InertJetsonServer;
import competition.subsystems.vision.JetsonServer;
import competition.subsystems.vision.NetworkedJetsonServer;
import xbot.common.command.BaseCommand;
import xbot.common.command.BaseRobot;
import xbot.common.injection.RobotModule;

public class Robot extends BaseRobot {
    
    AutonomousModeSelector autonomousModeSelector;
    
    public Robot() {
        super();
        
        this.injectionModule = new RobotModule() {
            @Override
            protected void configure() {
                this.bind(JetsonServer.class).to(NetworkedJetsonServer.class);
                super.configure();
            }
        };
    }
    
    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
        
        // Always running sensor updating commands
        this.injector.getInstance(UpdateArmSensorsCommand.class).start();
        
        this.autonomousModeSelector = this.injector.getInstance(AutonomousModeSelector.class);
    }
    
    @Override
    public void autonomousInit() {
        this.autonomousCommand = this.autonomousModeSelector.getCurrentAutonomousCommand();
        // Base implementation will run the command
        super.autonomousInit();
    }
}
