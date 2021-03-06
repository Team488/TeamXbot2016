
package competition;

import org.apache.log4j.Logger;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import competition.subsystems.arm.arm_commands.UpdateArmSensorsCommand;
import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;
import competition.subsystems.autonomous.selection.AutonomousModeSelector;
import competition.subsystems.autonomous.selection.SetupRaiseArmAndTraverseCommand;
import competition.subsystems.collector.commands.UpdateCollectorSensorsCommand;
import competition.subsystems.hanger.hook_commands.UpdateHookSensorsCommand;
import competition.subsystems.hanger.winch_commands.UpdateWinchSensorsCommand;
import competition.subsystems.lighting.LightingSubsystem;
import competition.subsystems.lighting.UpdateLightingStateCommand;
import competition.subsystems.vision.InertJetsonServer;
import competition.subsystems.vision.JetsonServer;
import competition.subsystems.vision.NetworkedJetsonServer;
import xbot.common.command.BaseCommand;
import competition.subsystems.vision.commands.VisionTelemetryReporterCommand;
import edu.wpi.first.wpilibj.DriverStation;
import xbot.common.command.BaseRobot;
import xbot.common.injection.RobotModule;

public class Robot extends BaseRobot {

    static Logger log = Logger.getLogger(Robot.class);
    
    AutonomousModeSelector autonomousModeSelector;
    ArmSubsystem arm;
    LightingSubsystem lightingSubsystem;
    ArmTargetSubsystem armTarget;
    
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
        this.injector.getInstance(UpdateLightingStateCommand.class).start();
        
        this.autonomousModeSelector = this.injector.getInstance(AutonomousModeSelector.class);
        
        this.arm = this.injector.getInstance(ArmSubsystem.class);
        this.lightingSubsystem = this.injector.getInstance(LightingSubsystem.class);
        this.armTarget = this.injector.getInstance(ArmTargetSubsystem.class);
    }
    
    SetupRaiseArmAndTraverseCommand rat;
    
    @Override
    public void autonomousInit() {
        logMatchInfo();
        
        this.lightingSubsystem.setRobotEnabled(true);
        this.lightingSubsystem.setCurrentAlliance(DriverStation.getInstance().getAlliance());
        
        this.autonomousCommand = this.autonomousModeSelector.getCurrentAutonomousCommand();
        
        // Base implementation will run the command
        super.autonomousInit();
        
        this.injector.getInstance(VisionTelemetryReporterCommand.class).start();
        resetSystems();
    }
    
    @Override
    public void teleopInit() {
        logMatchInfo();
        
        this.lightingSubsystem.setRobotEnabled(true);
        this.lightingSubsystem.setCurrentAlliance(DriverStation.getInstance().getAlliance());
        
        super.teleopInit();
        
        resetSystems();
    }
    
    @Override
    public void disabledInit() {
        super.disabledInit();
        
        this.lightingSubsystem.setRobotEnabled(false);
        this.lightingSubsystem.setCurrentAlliance(DriverStation.getInstance().getAlliance());
    }
    
    private void resetSystems() {
        armTarget.setTargetAngle(arm.getArmAngle());
    }
    
    private void logMatchInfo() {
        DriverStation ds = DriverStation.getInstance();
        
        log.info("MATCH METADATA:"
                + " DS connected: " + ds.isDSAttached() + ";"
                + " Connected to FMS: " + ds.isFMSAttached() + ";"
                + " On alliance " + ds.getAlliance().name() + " at station #" + ds.getLocation() + ";"
                + " In autonomous: " + ds.isAutonomous() + ";"
                + " Battery voltage: " + ds.getBatteryVoltage());
    }
}
