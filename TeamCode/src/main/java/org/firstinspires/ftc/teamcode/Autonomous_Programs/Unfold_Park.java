package org.firstinspires.ftc.teamcode.Autonomous_Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumHardware;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

@Autonomous(name="Unfold-Park", group="Pushbot")
public class Unfold_Park  extends LinearOpMode {

    /* Declare OpMode members. */
    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();


    double Drive;
    double Turn;
    double HStrafe;
    double VStrafe;

    @Override
    public void runOpMode() {
        robot.HeightMotor = hardwareMap.dcMotor.get("HeightMotor");
        /* Arm code for HeightMotor, which raises the arm up and down*/
        robot.HeightMotor = hardwareMap.dcMotor.get("HeightMotor");
        robot.HeightMotor.setZeroPowerBehavior(BRAKE);
        robot.HeightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        robot.HeightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.HeightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int HeightUpPos = 300;
        int HeightDownPos = -100;
        int Htarget = 0;
        //--------------------------------------------------------------------------------------

        /* Arm code for ElbowMotor that acts like an elbow would */
        robot.ElbowMotor = hardwareMap.dcMotor.get("ElbowMotor");
        robot.ElbowMotor.setZeroPowerBehavior(BRAKE);
        robot.ElbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //while (HeightMotor.getCurrentPosition() != 0) {
        //idle();
        //}
        robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int ElbowUpPos = 450;
        int ElbowDownPos = 450;
        int Etarget = 0;
        //--------------------------------------------------------------------------------------
        Drive = 0.5;
        HStrafe = 0.5;


        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        robot.Servo0.setPosition(0);
        robot.Servo2.setPosition(0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step 1:  Unfold Arm by raising the height and opening the elbow
        robot.HeightMotor.setTargetPosition(HeightUpPos);
        robot.ElbowMotor.setTargetPosition(ElbowDownPos);
        if (robot.HeightMotor.isBusy()) {
            robot.HeightMotor.setPower(0.4);

        } else {
            robot.HeightMotor.setPower(0);
        }

        if (robot.ElbowMotor.isBusy()) {
            robot.ElbowMotor.setPower(0.4);

        } else {
            robot.ElbowMotor.setPower(0);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Unfold", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("target", HeightUpPos);
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("target", ElbowDownPos);
            telemetry.update();
        }

        // Step 2: Unfold Arm by putting elbow down
        robot.HeightMotor.setTargetPosition(HeightDownPos);
        if (robot.HeightMotor.isBusy()) {
            robot.HeightMotor.setPower(0.4);

        } else {
            robot.HeightMotor.setPower(0);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Unfold2", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("target", HeightDownPos);
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("target", ElbowDownPos);
            telemetry.update();
        }

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

        // Step 3: Drives forward for 0.7 seconds to park
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Leg 16: Drives forward 0.7 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

    }
}
