
package competition;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import competition.subsystems.arm.arm_commands.UpdateArmSensorsCommand;
import competition.subsystems.autonomous.selection.AutonomousModeSelector;
import competition.subsystems.collector.commands.UpdateCollectorSensorsCommand;
import competition.subsystems.hanger.hook_commands.UpdateHookSensorsCommand;
import competition.subsystems.hanger.winch_commands.UpdateWinchSensorsCommand;
import competition.subsystems.vision.InertJetsonServer;
import competition.subsystems.vision.JetsonServer;
import competition.subsystems.vision.NetworkedJetsonServer;
import xbot.common.command.BaseCommand;
import competition.subsystems.vision.commands.VisionTelemetryReporterCommand;
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
        this.injector.getInstance(UpdateHookSensorsCommand.class).start();
        this.injector.getInstance(UpdateWinchSensorsCommand.class).start();
        this.injector.getInstance(UpdateCollectorSensorsCommand.class).start();
        
        this.autonomousModeSelector = this.injector.getInstance(AutonomousModeSelector.class);
    }
    
    @Override
    public void autonomousInit() {
        this.autonomousCommand = this.autonomousModeSelector.getCurrentAutonomousCommand();
        // Base implementation will run the command
        super.autonomousInit();
        
        this.injector.getInstance(VisionTelemetryReporterCommand.class).start();
    }
}
