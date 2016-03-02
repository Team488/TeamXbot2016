package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.controls.sensors.JoystickButtonManager;
import xbot.common.controls.sensors.XJoystick;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.logging.RobotAssertionManager;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@Singleton
public class OperatorInterface {
    public XJoystick leftJoystick;
    public XJoystick rightJoystick;
    public XJoystick operatorJoystick;
    
    public JoystickButtonManager leftButtons; 
    public JoystickButtonManager rightButtons;
    public JoystickButtonManager operatorButtons;
    
    @Inject
    public OperatorInterface(WPIFactory factory, RobotAssertionManager assertionManager) {
        leftJoystick = factory.getJoystick(1);
        rightJoystick = factory.getJoystick(2);
        operatorJoystick = factory.getJoystick(3);

        leftJoystick.setXInversion(true);
        leftJoystick.setYInversion(true);
        rightJoystick.setXInversion(true);
        rightJoystick.setYInversion(true);
        operatorJoystick.setXInversion(true);
        operatorJoystick.setYInversion(true);
        
        leftButtons = new JoystickButtonManager(11, factory, assertionManager, leftJoystick);
        rightButtons = new JoystickButtonManager(11, factory, assertionManager, rightJoystick);
        operatorButtons = new JoystickButtonManager(12, factory, assertionManager, operatorJoystick);
    }
}

