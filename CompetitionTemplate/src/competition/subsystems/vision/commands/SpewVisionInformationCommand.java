package competition.subsystems.vision.commands;

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
        requires(visionSubsystem);
        setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        int numRects = visionSubsystem.getBoulderRects() == null ? 0 : visionSubsystem.getBoulderRects().length;
        int numSpatial = visionSubsystem.getBoulderInfo() == null ? 0 : visionSubsystem.getBoulderInfo().length;
        log.debug("Rects: " + numRects + ", " + "Spatial coords: " + numSpatial);
    }
}
