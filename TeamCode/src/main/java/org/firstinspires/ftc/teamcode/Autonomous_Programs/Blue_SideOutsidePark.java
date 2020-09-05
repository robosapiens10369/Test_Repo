package org.firstinspires.ftc.teamcode.Autonomous_Programs;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumHardware;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;


@Autonomous(name="(B)BuildStart Outside-Park", group="Pushbot")
public class Blue_SideOutsidePark extends LinearOpMode {

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



        // Step 1:  Strafe right for 1.3 second to get closer to the foundation
        robot.leftFront.setPower(HStrafe);
        robot.leftBack.setPower(-HStrafe);
        robot.rightFront.setPower(-HStrafe);
        robot.rightBack.setPower(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 1: Strafes right for 1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Step 2: Drives backwards towards the platform for 0.9 second
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.9)) {
            telemetry.addData("Path", "Leg 2: Drives backward for 0.9 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3: Set power to zero to latch on the servo
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 3: Set powers to zero %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        sleep(1000);
        robot.Servo2.setPosition(1);
        sleep(2000);


        // Step 4: Drives the foundation in the building zone for 1.8 seconds
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.8)) {
            telemetry.addData("Path", "Leg 4: Drives for 1.8 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 5: Drives backward for 0.15 second to stay an inch away from the wall
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.15)) {
            telemetry.addData("Path", "Leg 5: Drives backward 0.15 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 6: Set power of the motors to zero for the servo to unlatch
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 6: Set power zero %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Unlatch the servo from the platform
        sleep(500);
        robot.Servo2.setPosition(0);
        sleep(500);


        // Step 7: Strafes left for the foundation to be pushed for 2.1 seconds
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.1)) {
            telemetry.addData("Path", "Leg 7: Strafes left for 2.1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 8: Drives backward for 0.7 seconds to get near the foundation
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Leg 8: Drives backward for 0.7 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 9: Strafes right for one second to push the foundation in the build zone
        robot.leftFront.setPower(HStrafe);
        robot.leftBack.setPower(-HStrafe);
        robot.rightFront.setPower(-HStrafe);
        robot.rightBack.setPower(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path", "Leg 8: Strafes right for 1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 10: Strafes left for 0.3 second to get back out
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.3)) {
            telemetry.addData("Path", "Leg 10: Strafes left 0.3 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 11: Turns for 0.82 seconds
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.82)) {
            telemetry.addData("Path", "Leg 11: Turns for 0.82 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Step 12: Strafes right for 2.0 seconds to park
        robot.leftFront.setPower(HStrafe);
        robot.leftBack.setPower(-HStrafe);
        robot.rightFront.setPower(-HStrafe);
        robot.rightBack.setPower(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Path", "Leg 12: Strafes right 2.0 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 13: Drives backward for 0.2 seconds
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.2)) {
            telemetry.addData("Path", "Leg 13: Drives backward for 0.2 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

        // Step 14:  Unfold Arm by raising the height and opening the elbow
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

        // Step 15: Unfold Arm by putting elbow down
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

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

        // Step 16: Drives forward for 0.7 seconds to park
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Leg 16: Drives forward 0.7 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 17: Strafes right for 0.3 seconds to park
        robot.leftFront.setPower(HStrafe);
        robot.leftBack.setPower(-HStrafe);
        robot.rightFront.setPower(-HStrafe);
        robot.rightBack.setPower(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.3)) {
            telemetry.addData("Path", "Leg 17: Strafes right 0.3 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

    }
}



