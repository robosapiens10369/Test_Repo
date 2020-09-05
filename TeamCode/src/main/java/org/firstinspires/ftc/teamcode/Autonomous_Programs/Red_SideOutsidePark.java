package org.firstinspires.ftc.teamcode.Autonomous_Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumHardware;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;


@Autonomous(name="(R)Red-Start Outside-Park", group="Pushbot")
public class Red_SideOutsidePark extends LinearOpMode {

    /* Declare OpMode members. */
    MecanumHardware         robot   = new MecanumHardware();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();


    double Drive;
    double Turn;
    double HStrafe;
    double VStrafe;

    @Override
    public void runOpMode() {

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

        // Step 1: Strafes left for 1.5 seconds towards the wall
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.5)) {
            telemetry.addData("Path", "Leg 1: Strafes left for 1.5 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Step 2: Drives backwards for 1 second to grab the foundation
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path", "Leg 2: Drives backward for 1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3: Servos set power to zero
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 3: Servos zero %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        sleep(1000);
        robot.Servo2.setPosition(1);
        sleep(2000);


        // Step 4: Drives forward and drags the foundation to the build zone for 1.8 sec
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.8)) {
            telemetry.addData("Path", "Leg 4: Drives forward for 1.8 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 5: Drives backward for 0.1 sec to get an inch away from the wall
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 5: drives backward for 0.1 sec%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }



        // Step 6: Set power to zero
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 6: Set power to zero %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Unlatch the servo from the platform
        sleep(500);
        robot.Servo2.setPosition(0);
        sleep(500);


        // Step 7: Strafes right for 2.7 seconds
        robot.leftFront.setPower(HStrafe);
        robot.leftBack.setPower(-HStrafe);
        robot.rightFront.setPower(-HStrafe);
        robot.rightBack.setPower(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.7)) {
            telemetry.addData("Path", "Leg 7: Strafes right for 2.7 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 8: Drives backward for 0.7 seconds to get closer to the foundation
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Leg 8: Drives backward for 0.7 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 9: Strafes left for 1 second to push the foundation in the build zone completly
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path", "Leg 9: Strafes left for 1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 10: Strafes right for 0.5 second to come back from pushing the foundation in
        robot.leftFront.setPower(HStrafe);
        robot.leftBack.setPower(-HStrafe);
        robot.rightFront.setPower(-HStrafe);
        robot.rightBack.setPower(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 10: Strafes right for 0.5 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Step 11: Turns for 0.85 seconds to bring the arm down before parking
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.85)) {
            telemetry.addData("Path", "Leg 11: Turns for 0.85 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }



        // Step 12: Strafes left for 1.75 sec towards the wall for parking
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.75)) {
            telemetry.addData("Path", "Leg 12: Strafes left 1.75 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Stops the turn
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);


        // Step 13:  Unfold Arm by raising the height and opening the elbow
        robot.HeightMotor.setTargetPosition(HeightUpPos);
        robot.ElbowMotor.setTargetPosition(ElbowDownPos);
        if (robot.HeightMotor.isBusy()) {
            robot.HeightMotor.setPower(0.4);

        }else {
            robot.HeightMotor.setPower(0);
        }

        if (robot.ElbowMotor.isBusy()) {
            robot.ElbowMotor.setPower(0.4);

        }else {
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

        // Step 14: Unfold Arm by putting elbow down
        robot.HeightMotor.setTargetPosition(HeightDownPos);
        if (robot.HeightMotor.isBusy()) {
            robot.HeightMotor.setPower(0.4);

        }else {
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
        // Step 15: Drives forward for 0.8 seconds to park near the wall
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.8)) {
            telemetry.addData("Path", "Leg 15: Drives forward for 0.8 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 16: Strafes left for 0.05 sec for the robot to be near the wall and not bump into alliance robot
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.05)) {
            telemetry.addData("Path", "Leg 16: Strafes left for 0.05 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

    }
}








