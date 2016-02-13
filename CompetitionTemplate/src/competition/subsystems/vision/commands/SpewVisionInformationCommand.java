package competition.subsystems.vision.commands;

import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;

public class SpewVisionInformationCommand extends BaseCommand {
    static Logger log = Logger.getLogger(SpewVisionInformationCommand.class);
    
    private VisionSubsystem visionSubsystem;
    
    @Inject
    public SpewVisionInformationCommand(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(visionSubsystem.getBoulderRects() == null) {
            log.info("No rects!");
        }
        else {
            String str = "";
            for(Rectangle r : visionSubsystem.getBoulderRects()) {
                str += " " + r.toString();
            }
            
            log.info("Rects:" + str);
        }
    }

}
