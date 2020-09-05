package org.firstinspires.ftc.teamcode;

/**
 * Josh & Ms. Monica
 * The purpose of this program is to read the telemetry of the motors and report to the user so they can be tested
 */
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="[TEST] Encoder Test", group="K9bot")

public class EncoderTest extends LinearOpMode {


    /* Declare OpMode members. */
    MecanumHardware robot = new MecanumHardware();


    @Override
    public void runOpMode() {



        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Reset the encoders and run using encoder
        robot.ElbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.HeightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.ActuatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.HeightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.ActuatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.ElbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ElbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ActuatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {



            //Report the encoder values to the user
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("encoder-actuator", robot.ActuatorMotor.getCurrentPosition());
            telemetry.update();
            idle();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
