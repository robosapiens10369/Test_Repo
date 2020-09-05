/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Autonomous_Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumHardware;
import org.firstinspires.ftc.teamcode.Autonomous_Programs.MovementFunctions;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;


@Autonomous(name="(B)BuildStart Inside-Park", group="Pushbot")
public class BlueSideInsidePark extends LinearOpMode {

    // test git changes 2

    /* Declare OpMode members. */
    MecanumHardware         robot   = new MecanumHardware();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();
    MovementFunctions       mvmnt = new MovementFunctions();

    double Drive;
    double Turn;
    double HStrafe;
    double VStrafe;

    @Override
    public void runOpMode()
    {
        robot.HeightMotor = hardwareMap.dcMotor.get("HeightMotor");

        /* Arm code for HeightMotor, which raises the arm up and down*/

        robot.HeightMotor.setZeroPowerBehavior(BRAKE);
        robot.HeightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //while (HeightMotor.getCurrentPosition() != 0) {
        //idle();
        //}
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


        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        mvmnt.runOpMode();  //right strafing

        // Step 1: Strafe left for 1.1 second to get closer to the foundation
        robot.StrafeLf(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.1)) {
            telemetry.addData("Path", "Leg 1: Strafe right %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Step 2: Drive backwards for 1 second to grab the foundation
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 2: Drives backward 1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3: Set power to zero to latch on the servo
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 3: stops for 0.1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        sleep(1000);
        robot.Servo2.setPosition(1);
        sleep(2000);


        // Step 4: Drives forward for 1.8 seconds bringing the foundation in the build zone
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.8)) {
            telemetry.addData("Path", "Leg 4: Drives forward 1.8 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 5: Drives backward for 0.1 second to stay an inch away from the wall
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 5: Drives backward for 0.1 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 6: Stop the motors to leave the foundation
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 6: Stop the motors %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Unlatch the servo from the platform
        sleep(500);
        robot.Servo2.setPosition(0);
        sleep(500);


        // Step 7: Strafes left for 1.9 seconds to get away from the wall
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.9)) {
            telemetry.addData("Path", "Leg 7: Strafes left 1.9 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 8: Drives backward for 0.7 seconds to come near the foundation
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(-Drive);
        robot.rightBack.setPower(-Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Leg 8: Drives backward 0.7 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 9: Strafe right for 1.4 seconds to push foundation into build zone
        robot.leftFront.setPower(HStrafe);
        robot.leftBack.setPower(-HStrafe);
        robot.rightFront.setPower(-HStrafe);
        robot.rightBack.setPower(HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.4)) {
            telemetry.addData("Path", "Leg 9: Strafes right 1.4 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 10: Strafe left for 0.3 seconds away from platform
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.3)) {
            telemetry.addData("Path", "Leg 10: Strafes left 0.3 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 11: Drives forward for 0.2 second for turning
        robot.leftFront.setPower(Drive);
        robot.leftBack.setPower(Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.2)) {
            telemetry.addData("Path", "Leg 11: Drives forward 0.2 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 12: Turns for 0.7 seconds to bring the arm down
        robot.leftFront.setPower(-Drive);
        robot.leftBack.setPower(-Drive);
        robot.rightFront.setPower(Drive);
        robot.rightBack.setPower(Drive);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Leg 12: Turns 0.7 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);


        // Step 13: Unfold Arm by raising the height and opening the elbow
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
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Unfold2", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("target", HeightDownPos);
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("target", ElbowDownPos);
            telemetry.update();
        }
        robot.ElbowMotor.setPower(0);

        // Step 15: Strafes left for 0.65 seconds inside towards wall
        robot.leftFront.setPower(-HStrafe);
        robot.leftBack.setPower(HStrafe);
        robot.rightFront.setPower(HStrafe);
        robot.rightBack.setPower(-HStrafe);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.65)) {
            telemetry.addData("Path", "Leg 15: Strafes right 0.65 sec %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

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

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

    }
}



